create table distribuicao.ministro (cod_ministro number not null, nom_ministro varchar2(100) not null, constraint pk_ministro primary key (cod_ministro));

create table distribuicao.processo_distribuido (seq_processo number not null, cod_ministro number not null, constraint pk_processo_distribuido primary key (seq_processo));
alter table distribuicao.processo_distribuido add constraint fk_ministro_prdi foreign key (cod_ministro) references distribuicao.ministro(cod_ministro);

alter table distribuicao.distribuicao add column cod_ministro number;
alter table distribuicao.distribuicao add column tip_distribuicao varchar2(9);
alter table distribuicao.distribuicao add constraint ck_dist_tip_distribuicao check (tip_distribuicao in ('COMUM', 'PREVENCAO'));
alter table distribuicao.distribuicao add column txt_justificativa varchar2(1000);
alter table distribuicao.distribuicao add column sig_distribuidor varchar2(30);
alter table distribuicao.distribuicao add column dat_distribuicao date;
alter table distribuicao.distribuicao add constraint fk_ministro_dist foreign key (cod_ministro) references distribuicao.ministro(cod_ministro);

create table distribuicao.processo_prevento (seq_distribuicao number not null, seq_processo number not null, constraint pk_processo_prevento primary key (seq_distribuicao, seq_processo));
alter table distribuicao.processo_prevento add constraint fk_distribuicao_prpr foreign key (seq_distribuicao) references distribuicao.distribuicao(seq_distribuicao);
alter table distribuicao.processo_prevento add constraint fk_processo_distribuido_prpr foreign key (seq_processo) references distribuicao.processo_distribuido(seq_processo);

create table distribuicao.ministro_candidato (seq_distribuicao number not null, cod_ministro number not null, constraint pk_ministro_candidato primary key (seq_distribuicao, cod_ministro));
alter table distribuicao.ministro_candidato add constraint fk_distribuicao_mica foreign key (seq_distribuicao) references distribuicao.distribuicao(seq_distribuicao);
alter table distribuicao.ministro_candidato add constraint fk_ministro_mica foreign key (cod_ministro) references distribuicao.ministro(cod_ministro);

create table distribuicao.ministro_impedido (seq_distribuicao number not null, cod_ministro number not null, constraint pk_ministro_impedido primary key (seq_distribuicao, cod_ministro));
alter table distribuicao.ministro_impedido add constraint fk_distribuicao_miim foreign key (seq_distribuicao) references distribuicao.distribuicao(seq_distribuicao);
alter table distribuicao.ministro_impedido add constraint fk_ministro_miim foreign key (cod_ministro) references distribuicao.ministro(cod_ministro);

create table distribuicao.fila_distribuicao (seq_distribuicao number not null, seq_processo number not null, tip_status varchar2(12) not null, constraint pk_fila_distribuicao primary key (seq_distribuicao));
alter table distribuicao.fila_distribuicao add constraint ck_fidi_tip_status check (tip_status in ('DISTRIBUICAO', 'DISTRIBUIDO'));