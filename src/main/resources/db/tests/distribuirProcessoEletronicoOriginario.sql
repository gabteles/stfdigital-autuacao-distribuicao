SELECT SET(@proc_def_id_, ID_) FROM PUBLIC.ACT_RE_PROCDEF WHERE NAME_ = 'distribuicao';

INSERT INTO DISTRIBUICAO.FILA_DISTRIBUICAO(SEQ_DISTRIBUICAO, SEQ_PROCESSO, TIP_STATUS) VALUES
(9000, 9002, 'DISTRIBUICAO');         

INSERT INTO PUBLIC.ACT_GE_BYTEARRAY(ID_, REV_, NAME_, DEPLOYMENT_ID_, BYTES_, GENERATED_) VALUES
('-7', 1, 'var-informationId', NULL, X'aced00057372003c62722e6a75732e7374662e617574756163616f2e64697374726962756963616f2e646f6d61696e2e6d6f64656c2e44697374726962756963616f496400000000000000010200014c000269647400104c6a6176612f6c616e672f4c6f6e673b78707372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b02000078700000000000000001', NULL),
('-9', 1, 'hist.var-informationId', NULL, X'aced00057372003c62722e6a75732e7374662e617574756163616f2e64697374726962756963616f2e646f6d61696e2e6d6f64656c2e44697374726962756963616f496400000000000000010200014c000269647400104c6a6176612f6c616e672f4c6f6e673b78707372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b02000078700000000000000001', NULL);      

INSERT INTO PUBLIC.ACT_RU_EXECUTION(ID_, REV_, PROC_INST_ID_, BUSINESS_KEY_, PARENT_ID_, PROC_DEF_ID_, SUPER_EXEC_, ACT_ID_, IS_ACTIVE_, IS_CONCURRENT_, IS_SCOPE_, IS_EVENT_SCOPE_, SUSPENSION_STATE_, CACHED_ENT_STATE_, TENANT_ID_, NAME_, LOCK_TIME_) VALUES
('-4', 1, '-4', 'DT:9000', NULL, @proc_def_id_, NULL, 'distribuir-processo', TRUE, FALSE, TRUE, FALSE, 1, 2, NULL, NULL, NULL);  

INSERT INTO PUBLIC.ACT_RU_TASK(ID_, REV_, EXECUTION_ID_, PROC_INST_ID_, PROC_DEF_ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, TASK_DEF_KEY_, OWNER_, ASSIGNEE_, DELEGATION_, PRIORITY_, CREATE_TIME_, DUE_DATE_, CATEGORY_, SUSPENSION_STATE_, TENANT_ID_, FORM_KEY_) VALUES
('-11', 1, '-4', '-4', @proc_def_id_, 'Distribuir Processo', NULL, 'DISTRIBUICAO', 'distribuir-processo', NULL, NULL, NULL, 50, TIMESTAMP '2016-06-02 16:01:08.766', NULL, NULL, 1, NULL, NULL);  

INSERT INTO PUBLIC.ACT_RU_IDENTITYLINK(ID_, REV_, GROUP_ID_, TYPE_, USER_ID_, TASK_ID_, PROC_INST_ID_, PROC_DEF_ID_) VALUES
('-12', 1, 'distribuidor', 'candidate', NULL, '-11', NULL, NULL); 

INSERT INTO PUBLIC.ACT_RU_VARIABLE(ID_, REV_, TYPE_, NAME_, EXECUTION_ID_, PROC_INST_ID_, TASK_ID_, BYTEARRAY_ID_, DOUBLE_, LONG_, TEXT_, TEXT2_) VALUES
('-6', 1, 'string', 'transition', '-4', '-4', NULL, NULL, NULL, NULL, NULL, NULL),
('-8', 1, 'serializable', 'informationId', '-4', '-4', NULL, '-7', NULL, NULL, NULL, NULL);           

INSERT INTO PUBLIC.ACT_HI_PROCINST(ID_, PROC_INST_ID_, BUSINESS_KEY_, PROC_DEF_ID_, START_TIME_, END_TIME_, DURATION_, START_USER_ID_, START_ACT_ID_, END_ACT_ID_, SUPER_PROCESS_INSTANCE_ID_, DELETE_REASON_, TENANT_ID_, NAME_) VALUES
('-4', '-4', 'DT:9000', @proc_def_id_, TIMESTAMP '2016-06-02 16:01:08.742', NULL, NULL, NULL, 'inicio', NULL, NULL, NULL, NULL, NULL);    

INSERT INTO PUBLIC.ACT_HI_ACTINST(ID_, PROC_DEF_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_ID_, TASK_ID_, CALL_PROC_INST_ID_, ACT_NAME_, ACT_TYPE_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, TENANT_ID_) VALUES
('-5', @proc_def_id_, '-4', '-4', 'inicio', NULL, NULL, NULL, 'startEvent', NULL, TIMESTAMP '2016-06-02 16:01:08.743', TIMESTAMP '2016-06-02 16:01:08.766', 23, NULL),
('-10', @proc_def_id_, '-4', '-4', 'distribuir-processo', '-11', NULL, 'Distribuir Processo', 'userTask', NULL, TIMESTAMP '2016-06-02 16:01:08.766', NULL, NULL, NULL);          

INSERT INTO PUBLIC.ACT_HI_TASKINST(ID_, PROC_DEF_ID_, TASK_DEF_KEY_, PROC_INST_ID_, EXECUTION_ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, OWNER_, ASSIGNEE_, START_TIME_, CLAIM_TIME_, END_TIME_, DURATION_, DELETE_REASON_, PRIORITY_, DUE_DATE_, FORM_KEY_, CATEGORY_, TENANT_ID_) VALUES
('-11', @proc_def_id_, 'distribuir-processo', '-4', '-4', 'Distribuir Processo', NULL, 'DISTRIBUICAO', NULL, NULL, TIMESTAMP '2016-06-02 16:01:08.766', NULL, NULL, NULL, NULL, 50, NULL, NULL, NULL, NULL);          

INSERT INTO PUBLIC.ACT_HI_VARINST(ID_, PROC_INST_ID_, EXECUTION_ID_, TASK_ID_, NAME_, VAR_TYPE_, REV_, BYTEARRAY_ID_, DOUBLE_, LONG_, TEXT_, TEXT2_, CREATE_TIME_, LAST_UPDATED_TIME_) VALUES
('-6', '-4', '-4', NULL, 'transition', 'string', 0, NULL, NULL, NULL, NULL, NULL, TIMESTAMP '2016-06-02 16:01:08.745', TIMESTAMP '2016-06-02 16:01:08.745'),
('-8', '-4', '-4', NULL, 'informationId', 'serializable', 0, '-9', NULL, NULL, NULL, NULL, TIMESTAMP '2016-06-02 16:01:08.753', TIMESTAMP '2016-06-02 16:01:08.753');              

INSERT INTO PUBLIC.ACT_HI_IDENTITYLINK(ID_, GROUP_ID_, TYPE_, USER_ID_, TASK_ID_, PROC_INST_ID_) VALUES
('-12', 'distribuidor', 'candidate', NULL, '-11', NULL);       