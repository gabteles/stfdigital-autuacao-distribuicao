create table distribuicao.ministro (cod_ministro number not null, nom_ministro varchar2(100) not null, constraint pk_ministro primary key (cod_ministro));

create table distribuicao.processo (seq_processo number not null, cod_ministro number not null, constraint pk_processo primary key (seq_processo));
alter table distribuicao.processo add constraint fk_ministro_proc foreign key (cod_ministro) references distribuicao.ministro(cod_ministro);

alter table distribuicao.distribuicao add column cod_ministro number;
alter table distribuicao.distribuicao add column tip_distribuicao varchar2(9);
alter table distribuicao.distribuicao add constraint ck_dist_tip_distribuicao check (tip_distribuicao in ('COMUM', 'PREVENCAO'));
alter table distribuicao.distribuicao add column txt_justificativa varchar2(1000);
alter table distribuicao.distribuicao add column sig_distribuidor varchar2(30);
alter table distribuicao.distribuicao add column dat_distribuicao date;
alter table distribuicao.distribuicao add constraint fk_ministro_dist foreign key (cod_ministro) references distribuicao.ministro(cod_ministro);

create table distribuicao.processo_prevento (seq_distribuicao number not null, seq_processo number not null, constraint pk_processo_prevento primary key (seq_distribuicao, seq_processo));
alter table distribuicao.processo_prevento add constraint fk_distribuicao_prpr foreign key (seq_distribuicao) references distribuicao.distribuicao(seq_distribuicao);
alter table distribuicao.processo_prevento add constraint fk_processo_prpr foreign key (seq_processo) references distribuicao.processo(seq_processo);

create table distribuicao.ministro_candidato (seq_distribuicao number not null, cod_ministro number not null, constraint pk_ministro_candidato primary key (seq_distribuicao, cod_ministro));
alter table distribuicao.ministro_candidato add constraint fk_distribuicao_mica foreign key (seq_distribuicao) references distribuicao.distribuicao(seq_distribuicao);
alter table distribuicao.ministro_candidato add constraint fk_ministro_mica foreign key (cod_ministro) references distribuicao.ministro(cod_ministro);

create table distribuicao.ministro_impedido (seq_distribuicao number not null, cod_ministro number not null, constraint pk_ministro_impedido primary key (seq_distribuicao, cod_ministro));
alter table distribuicao.ministro_impedido add constraint fk_distribuicao_miim foreign key (seq_distribuicao) references distribuicao.distribuicao(seq_distribuicao);
alter table distribuicao.ministro_impedido add constraint fk_ministro_miim foreign key (cod_ministro) references distribuicao.ministro(cod_ministro);