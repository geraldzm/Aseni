IF DB_ID('aseni') IS NULL
    create database aseni
go

use aseni
go

IF OBJECT_ID(N'dbo.user') IS NOT NULL
    DROP TABLE [dbo].[user];
GO
CREATE TABLE [user] (
  [user_id] int PRIMARY KEY IDENTITY(1, 1),
  [name] varchar(32),
  [bio] varchar(256),
  [rol_id] int,
  [political_party_id] int,
  [foto] int
)
GO

IF OBJECT_ID(N'dbo.rol') IS NOT NULL
    DROP TABLE [dbo].[rol];
GO
CREATE TABLE [rol] (
  [rol_id] int PRIMARY KEY IDENTITY(1, 1),
  [name] varchar(32) UNIQUE NOT NULL
)
GO

IF OBJECT_ID(N'dbo.deliverable') IS NOT NULL
    DROP TABLE [dbo].[deliverable];
GO
CREATE TABLE [deliverable] (
   [deliverable_id] int PRIMARY KEY IDENTITY(1, 1),
   [deadline] datetime NOT NULL,
   [kpi] int NOT NULL,
   [action_id] int,
   [kpi_type_id] int,
   [canton_id] int,
   [plan_id] int
)
GO

IF OBJECT_ID(N'dbo.canton') IS NOT NULL
    DROP TABLE [dbo].[canton];
GO
CREATE TABLE [canton] (
  [canton_id] int PRIMARY KEY IDENTITY(1, 1),
  [name] varchar(32) NOT NULL
)
GO


IF OBJECT_ID(N'dbo.plan') IS NOT NULL
    DROP TABLE [dbo].[plan];
GO
CREATE TABLE [plan] (
  [plan_id] int PRIMARY KEY IDENTITY(1, 1),
  [title] varchar(32) NOT NULL,
  [description] varchar(512) NOT NULL,
  [pp_id] int
)
GO

IF OBJECT_ID(N'dbo.political_party') IS NOT NULL
    DROP TABLE [dbo].[political_party];
GO
CREATE TABLE [political_party] (
  [pp_id] int PRIMARY KEY IDENTITY(1, 1),
  [name] varchar(32),
  [flag_image] int
)
GO

IF OBJECT_ID(N'dbo.action') IS NOT NULL
    DROP TABLE [dbo].[action];
GO
CREATE TABLE [action] (
  [action_id] int PRIMARY KEY IDENTITY(1, 1),
  [action] varchar(512) NOT NULL
)
GO

IF OBJECT_ID(N'dbo.kpi_type') IS NOT NULL
    DROP TABLE [dbo].[kpi_type];
GO
CREATE TABLE [kpi_type] (
  [kpi_type_id] int PRIMARY KEY IDENTITY(1, 1),
  [name] varchar(32)
)
GO

IF OBJECT_ID(N'dbo.image') IS NOT NULL
    DROP TABLE [dbo].[image];
GO
CREATE TABLE [image] (
  [image_id] int PRIMARY KEY IDENTITY(1, 1),
  [url] varchar(512)
)
GO

ALTER TABLE [user] ADD FOREIGN KEY ([rol_id]) REFERENCES [rol] ([rol_id])
GO

ALTER TABLE [user] ADD FOREIGN KEY ([political_party_id]) REFERENCES [political_party] ([pp_id])
GO

ALTER TABLE [user] ADD FOREIGN KEY ([foto]) REFERENCES [image] ([image_id])
GO

ALTER TABLE [political_party] ADD FOREIGN KEY ([flag_image]) REFERENCES [image] ([image_id])
GO

ALTER TABLE [plan] ADD FOREIGN KEY ([pp_id]) REFERENCES [political_party] ([pp_id])
GO

ALTER TABLE [deliverable] ADD FOREIGN KEY ([action_id]) REFERENCES [action] ([action_id])
GO

ALTER TABLE [deliverable] ADD FOREIGN KEY ([kpi_type_id]) REFERENCES [kpi_type] ([kpi_type_id])
GO

ALTER TABLE [deliverable] ADD FOREIGN KEY ([canton_id]) REFERENCES [canton] ([canton_id])
GO

ALTER TABLE [deliverable] ADD FOREIGN KEY ([plan_id]) REFERENCES [plan] ([plan_id])
GO

-- ############################################################ INSERTS ############################################################

insert into rol (name) values ('manager'), ('user');
insert into canton (name) values ('Alajuela'), ('Cartago'), ('San jose'), ('Grecia'), ('Paraíso'), ('El Guarco'), ('Oreamuno'), ('Jimenez'), ('Alvarado'), ('Turrialba');
insert into kpi_type (name) values  ('Km'), ('Escuelas'),  ('Startups');
insert into image (url) values ('https://ksdfjweiCDIf'), ('https://cvbfgdssf'), ('https://xcvber'),
                               ('https://adfwef'), ('https://asdf'), ('https://dfgadfg'),
                               ('https://dfgsdfg'), ('https://xcvxcvs'), ('https://zxcvsd'), ('https://qerfqe');
insert into political_party (name, flag_image) values ('PLN', 1), ('PSD', 2), ('PAC', 3), ('PUSC', 4);

insert into [plan] (title, description, pp_id) values ('plan 1', 'mi gran plan 1', 1), ('plan 2', 'mi gran plan 2', 2),
                                                      ('plan 3', 'mi gran plan 3', 3), ('plan 4', 'mi gran plan 4', 4);

insert into [user] (rol_id, political_party_id, name, foto, bio) values
 (1, 1, 'José Figueres', 5, 'Director Marketing'),
 (1, 2, 'Rodrígo Chavez', 6, 'Economista'),
 (1, 3, 'Dimitri Perez', 7, 'Ing en Computacion'),
 (1, 4, 'Maria Dinarte', 8, 'Biologa'),
 (2, 1, 'Juan Perez', 9, 'Ing Eletrica'),
 (2, 2, 'Andrew Gomez', 10, 'Quimico');

insert into action (action) values
('Asfaltado o restauracion de las carretera'),
('Construcción de escuelas'),
('Creación de empresas tecnológicas');
-- ('Población vacunada');


-- creating deliverables

DECLARE
    @parties INT = (select count(*) from political_party),
    @actions INT,
    @deliveries INT,
    @c INT;



while @parties > 0 -- per party
begin

    select @actions = count(*) from action;
    while @actions > 0 -- per action
    begin
        -- select random cantons
        select canton_id, ABS(CHECKSUM(NewId())) as weight
        into #canton_ids
        from canton order by weight;

        set @deliveries = FLOOR(RAND()*(7-3+1))+3; -- 3 >= @deliveries <= 7

        while @deliveries > 0 -- 3 to 7 deliveries per action
        begin

            select top 1 @c = canton_id from #canton_ids;
            insert into deliverable (deadline, kpi, action_id, kpi_type_id, canton_id, plan_id)
            values (DATEADD(year, FLOOR(RAND()*(5-2+1))+2, getdate()), FLOOR(RAND()*10)+1, @actions, @actions, @c, @parties);
            delete from #canton_ids where canton_id = @c;

            set @deliveries = @deliveries - 1;
        end

        drop table #canton_ids;
        set @actions = @actions - 1;
    end

    set @parties = @parties - 1
end

select d.deadline, c.name, a.action, p.title
from deliverable d
inner join canton c on d.canton_id = c.canton_id
inner join action a on d.action_id = a.action_id
inner join [plan] p on d.plan_id = p.plan_id;


-- Query1: listar todos los entregables agrupados por partido para un cantón dado por parámetro, y cada
-- hilo usa un cantón diferente.
DECLARE @paramCanton VARCHAR(32) = 'Cartago';
select pp.name, a.action, c.name
from deliverable d
inner join [plan] p on d.plan_id = p.plan_id
inner join action a on a.action_id = d.action_id
inner join canton c on c.canton_id = d.canton_id
inner join political_party pp on p.pp_id = pp.pp_id
where c.name = @paramCanton
group by pp.name, a.action, c.name
order by pp.name;


-- Query2: listar todos los cantones que van a recibir entregables de máximo el 25% de los partidos,
-- agregando la cantidad de entregables por cantón
DECLARE @maxP int = FLOOR((select count(*) from political_party) * 0.50); -- maxP = 25% of the amount of parties
WITH R as (
    select c.name as canton, count(*) as amount
    from deliverable d
    inner join canton c on d.canton_id = c.canton_id
    inner join [plan] p on d.plan_id = p.plan_id
    inner join political_party pp on p.pp_id = pp.pp_id
    group by pp.name, c.name
)
select canton, count(*) as parties, sum(amount) as deliveries
from R
group by canton
having count(*) <= @maxP;


-- Query3: Para cada partido y para cada acción del plan mostrar el cantón con menos entregables y la
-- cantidad, y el cantón con más entregables y la cantidad.

-- max
select pp_name, action, kpi, c_name, 'max' as type
from (
    select pp.name as pp_name, a.action as action, d.kpi as kpi, c.name as c_name, row_number() OVER (PARTITION BY pp.name, a.action ORDER BY kpi ) as row_n
    from deliverable d
    inner join canton c on d.canton_id = c.canton_id
    inner join [plan] p on d.plan_id = p.plan_id
    inner join political_party pp on p.pp_id = pp.pp_id
    inner join action a on d.action_id = a.action_id
) as rs
where rs.row_n = 1
UNION
-- min
select pp_name, action, kpi, c_name, 'min' as type
from (
    select pp.name as pp_name, a.action as action, d.kpi as kpi, c.name as c_name, row_number() OVER (PARTITION BY pp.name, a.action ORDER BY kpi DESC) as row_n
    from deliverable d
    inner join canton c on d.canton_id = c.canton_id
    inner join [plan] p on d.plan_id = p.plan_id
    inner join political_party pp on p.pp_id = pp.pp_id
    inner join action a on d.action_id = a.action_id
) as rs
where rs.row_n = 1;



select 'done :)';

