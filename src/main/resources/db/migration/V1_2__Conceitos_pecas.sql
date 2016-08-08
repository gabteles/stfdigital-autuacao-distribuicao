create sequence distribuicao.seq_peca increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table distribuicao.tipo_peca (seq_tipo_documento number not null, nom_tipo_documento varchar2(100) not null, constraint pk_tipo_peca primary key (seq_tipo_documento));

create table distribuicao.peca (seq_peca number not null, seq_processo number not null, dsc_peca varchar2(100) not null, seq_tipo_peca number not null, seq_documento number not null, num_ordem integer not null, tip_situacao varchar2(16) not null, tip_visibilidade varchar2(21) not null, constraint pk_peca primary key (seq_peca));
alter table distribuicao.peca add constraint ck_peca_tip_visibilidade check (tip_visibilidade in ('PUBLICO', 'PENDENTE_VISUALIZACAO'));
alter table distribuicao.peca add constraint ck_peca_tip_situacao check (tip_situacao in ('EXCLUIDA', 'JUNTADA', 'PENDENTE_JUNTADA'));
alter table distribuicao.peca add constraint fk_tipo_peca_peca foreign key (seq_tipo_peca) references distribuicao.tipo_peca(seq_tipo_documento);
alter table distribuicao.peca add constraint fk_processo_peca foreign key (seq_processo) references distribuicao.processo(seq_processo);