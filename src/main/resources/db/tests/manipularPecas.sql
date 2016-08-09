INSERT INTO DISTRIBUICAO.FILA_DISTRIBUICAO(SEQ_DISTRIBUICAO, SEQ_PROCESSO, TIP_STATUS) VALUES
(9003, 9002, 'DISTRIBUIDO');

INSERT INTO DISTRIBUICAO.DISTRIBUICAO(SEQ_DISTRIBUICAO, SEQ_PROCESSO, TIP_DISTRIBUICAO, COD_MINISTRO, SIG_DISTRIBUIDOR, DAT_DISTRIBUICAO, TIP_STATUS) VALUES
(9003, 9002, 'COMUM', 42, 'distribuidor', SYSDATE, 'DISTRIBUIDO');

INSERT INTO DISTRIBUICAO.PROCESSO(SEQ_PROCESSO, COD_MINISTRO) VALUES
(9002, 42);

INSERT INTO distribuicao.peca (seq_peca, seq_processo, dsc_peca, seq_tipo_peca, seq_documento, num_ordem, tip_situacao, tip_visibilidade) VALUES
(9000, 9002, 'Petição inicial', 101, 1, 1, 'JUNTADA', 'PUBLICO');

INSERT INTO distribuicao.peca (seq_peca, seq_processo, dsc_peca, seq_tipo_peca, seq_documento, num_ordem, tip_situacao, tip_visibilidade) VALUES
(9001, 9002, 'Despacho', 1060, 2, 2, 'PENDENTE_JUNTADA', 'PUBLICO');