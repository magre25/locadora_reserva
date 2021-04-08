-- Database: Locadora

-- DROP DATABASE "Locadora";

create table cliente(
cpf_cliente	numeric(11) primary key,
nome varchar(25) not null,
telefone varchar(25) not null
);

create table categoria(
cod_categoria	integer primary key,
descricao	varchar(20) not null,
preco_diaria	numeric(8,2)  not null,
preco_multa_dia	numeric(8,2)
);

create table carro(
placa		varchar(8) primary key,
marca		varchar(10),
ano	        numeric(4),
modelo		varchar(15),
situacao	varchar(4) not null,
cod_categoria	int,
foreign key(cod_categoria) references categoria(cod_categoria)
);

create table reserva(
cod_reserva	serial primary key,
data_prev_ret   date not null,
data_prev_dev   date not null,
cpf_cliente	numeric(11) not null,
placa		varchar(8) not null,
foreign key (cpf_cliente) references cliente(cpf_cliente) on update cascade on delete cascade,
foreign key(placa) references carro(placa) on update cascade on delete cascade
);

-- adicionando coluna contendo a data e a hora da reserva
alter table reserva add column data_reserva timestamp not null default current_timestamp(0); --(0) precisao das casas de segundos
--alter table reserva drop column data_reserva;

-- INSERINDO DADOS
insert into cliente (cpf_cliente, nome, telefone) values
(41027643789, 'Renan Costa', '16992129021'),
(34190822161, 'Juliana Santos Barbosa', '1698762134'),
(42524151875, 'Marcos Gregório Ignacio', '1633378921'),
(32567109822, 'Arnaldo Ribeiro', '16993418754');

insert into categoria (cod_categoria, descricao, preco_diaria, preco_multa_dia) values
(90, 'Econmico', 100, 10);
insert into categoria (cod_categoria, descricao, preco_diaria, preco_multa_dia) values
(91, 'Familiar', 150, 15);
insert into categoria (cod_categoria, descricao, preco_diaria, preco_multa_dia) values
(92, 'Conversivel', 250, 25);
insert into categoria (cod_categoria, descricao, preco_diaria, preco_multa_dia) values
(93, 'Luxo', 300, 30);

insert into carro (placa, marca, ano, modelo, situacao, cod_categoria) values
('NEZ-5751', 'Fiat', 2018, 'Uno', 'disponivel', 90);
insert into carro (placa, marca, ano, modelo, situacao, cod_categoria) values
('LMG-1151', 'Renaut', 2018, 'Logan', 'disponivel', 91);
insert into carro (placa, marca, ano, modelo, situacao, cod_categoria) values
('MUY-5853', 'Hyundai', 2020, 'HB20', 'disponivel', 92);
insert into carro (placa, marca, ano, modelo, situacao, cod_categoria) values
('JQG-2406', 'Mercedes', 2020, 'SLK', 'disponivel', 93);
insert into carro (placa, marca, ano, modelo, situacao, cod_categoria) values
('JVO-3864', 'Porsche', 2021, 'GT2 RS', 'disponivel', 93);

insert into reserva (cod_reserva, data_prev_ret, data_prev_dev, cpf_cliente, placa) values
(1, '21/01/2021', '29/01/2021', 42524151875,'MUY-5853');

-- CONSULTA COMPLEXAS
create view reservas as
select cod_reserva, data_prev_ret as data_retirada, data_prev_dev as data_devolucao, nome as cliente, 
modelo as carro                             
from reserva r
join cliente cl
using(cpf_cliente)
join carro ca
using(placa)
order by data_prev_ret desc; 

select * from reservas;

-- PROCEDIMENTO QUE VERIFICA SE O CARRO ESTA DISPONIVEL OU NAO 
CREATE OR REPLACE FUNCTION proc_reserva(placa_car varchar)
RETURNS integer
AS $$
DECLARE
 situ_car carro.situacao%TYPE;
BEGIN
	select situacao into situ_car
	from carro 
	where placa = placa_car;
	
	if (situ_car = 'disponivel') then
		update carro set situacao = 'reservado'
		where placa = placa_car;
		commit;
		RETURN 1;
	else 
		raise exception 'car_off';
		rollback; 
	end if;
END;
$$ language plpgsql;

-- TESTES 
select * from carro;
select * from reserva;
select proc_reserva('LMG-1151');