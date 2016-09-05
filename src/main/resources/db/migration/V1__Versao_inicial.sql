create schema distribuicao;

create sequence distribuicao.seq_distribuicao increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create sequence distribuicao.seq_evento increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table distribuicao.distribuicao (seq_distribuicao number not null, seq_processo number not null, tip_status varchar2(12) not null, constraint pk_distribuicao primary key (seq_distribuicao));
alter table distribuicao.distribuicao add constraint ck_dist_tip_status check (tip_status in ('DISTRIBUICAO', 'DISTRIBUIDO'));

create table distribuicao.evento (seq_evento number not null, nom_evento varchar2(100) not null, dat_criacao date not null, bin_detalhe clob not null, tip_status smallint not null, constraint pk_evento primary key (seq_evento));

create table distribuicao.distribuicao_evento (seq_distribuicao number not null, seq_evento number not null, constraint pk_distribuicao_evento primary key (seq_distribuicao, seq_evento));