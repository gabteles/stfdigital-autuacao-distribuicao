create schema distribuicao;

create sequence distribuicao.seq_distribuicao increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table distribuicao.distribuicao (seq_distribuicao number not null, seq_processo number not null, tip_status varchar2(15) not null, constraint pk_distribuicao primary key (seq_distribuicao));
alter table distribuicao.distribuicao add constraint ck_dist_tip_status check (tip_status in ('DISTRIBUICAO', 'DISTRIBUIDO'));