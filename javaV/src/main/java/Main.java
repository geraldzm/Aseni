
import javax.persistence.*;
import java.util.List;

public class Main {

    static EntityManagerFactory factory; // pool manager
    static int counter;

    public static synchronized void finish() {
//        System.out.println("Thread" + Thread.currentThread().getId() + " finished: " + counter);
        if(++counter == 10) {
            System.out.println("end");
            factory.close();
        }
    }

    public static void query1(String canton) {

        EntityManager manager = factory.createEntityManager();

        StoredProcedureQuery storedProcedureQuery = manager.createStoredProcedureQuery("query_1")
                .registerStoredProcedureParameter("@paramCanton", String.class, ParameterMode.IN)
                .setParameter("@paramCanton", canton);

        long start = System.currentTimeMillis();
        storedProcedureQuery.execute();
        long end = System.currentTimeMillis();

        System.out.println("-> Thread " + Thread.currentThread().getId() + "\t" + (end - start) + " ms, found records with " + canton + ": " + storedProcedureQuery.getResultList().size());
        finish();
    }

    public static void testQuery1() {
        // comentar en el archivo persistence.xml las linesas del pool

        String[] cantones = { "Alajuela", "Cartago", "San jose", "Grecia", "Paraíso", "El Guarco", "Oreamuno", "Jimenez", "Alvarado", "Turrialba" };
        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> query1(cantones[finalI]) );
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Thread t : threads)  t.start();

    }


    private static void query2() {

        EntityManager manager = factory.createEntityManager();
        Query nativeQuery = manager.createNativeQuery(
                "DECLARE @maxP int = FLOOR((select count(*) from political_party) * 0.25); -- maxP = 25% of the amount of parties\n" +
                        "WITH R as (\n" +
                        "    select c.name as canton, count(*) as amount\n" +
                        "    from deliverable d\n" +
                        "    inner join canton c on d.canton_id = c.canton_id\n" +
                        "    inner join [plan] p on d.plan_id = p.plan_id\n" +
                        "    inner join political_party pp on p.pp_id = pp.pp_id\n" +
                        "    group by pp.name, c.name\n" +
                        ")\n" +
                        "select canton, count(*) as parties, sum(amount) as deliveries\n" +
                        "from R\n" +
                        "group by canton\n" +
                        "having count(*) <= @maxP;");

        long start = System.currentTimeMillis();
        List resultList = nativeQuery.getResultList();
        long end = System.currentTimeMillis();

        System.out.println("-> Thread " + Thread.currentThread().getId() + "\t" + (end - start) + " ms, found records: " + resultList.size());
        finish();
    }

    public static void testQuery2() {

        // descomentar en el archivo persistence.xml lo del pool

        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(Main::query2);
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Thread t : threads)  t.start();

    }

    private static void query3() {

        EntityManager manager = factory.createEntityManager();
        factory.getCache().evictAll();
        Query nativeQuery = manager.createNativeQuery("select pp_name, action, kpi, c_name, 'max' as type\n" +
                "from (\n" +
                "    select pp.name as pp_name, a.action as action, d.kpi as kpi, c.name as c_name, row_number() OVER (PARTITION BY pp.name, a.action ORDER BY kpi ) as row_n\n" +
                "    from deliverable d\n" +
                "    inner join canton c on d.canton_id = c.canton_id\n" +
                "    inner join [plan] p on d.plan_id = p.plan_id\n" +
                "    inner join political_party pp on p.pp_id = pp.pp_id\n" +
                "    inner join action a on d.action_id = a.action_id\n" +
                ") as rs\n" +
                "where rs.row_n = 1\n" +
                "UNION\n" +
                "-- min\n" +
                "select pp_name, action, kpi, c_name, 'min' as type\n" +
                "from (\n" +
                "    select pp.name as pp_name, a.action as action, d.kpi as kpi, c.name as c_name, row_number() OVER (PARTITION BY pp.name, a.action ORDER BY kpi DESC) as row_n\n" +
                "    from deliverable d\n" +
                "    inner join canton c on d.canton_id = c.canton_id\n" +
                "    inner join [plan] p on d.plan_id = p.plan_id\n" +
                "    inner join political_party pp on p.pp_id = pp.pp_id\n" +
                "    inner join action a on d.action_id = a.action_id\n" +
                ") as rs\n" +
                "where rs.row_n = 1;\n");


        long start = System.currentTimeMillis();
        List resultList = nativeQuery.getResultList();
        long end = System.currentTimeMillis();

        System.out.println("-> Thread " + Thread.currentThread().getId() + "\t" + (end - start) + " ms, found records: " + resultList.size());
        finish();
    }

    private static void testQuery3() {

        // descomentar en el archivo persistence.xml lo del pool
        // descomentar en el archivo persistence.xml lo de la cache

        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(Main::query3);
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Thread t : threads)  t.start();
    }

    public static void main(String[] args) {

        factory = Persistence.createEntityManagerFactory("default");
        testQuery1();
//        testQuery2();
//        testQuery3();

    }



}
