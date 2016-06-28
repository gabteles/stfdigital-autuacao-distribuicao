delete DISTRIBUICAO.FILA_DISTRIBUICAO where SEQ_DISTRIBUICAO = 9002;         

delete PUBLIC.ACT_HI_PROCINST where ID_ = '-22';    

delete PUBLIC.ACT_HI_ACTINST where ID_ in ('-23', '-28');          

delete PUBLIC.ACT_HI_TASKINST where ID_ = '-29';          

delete PUBLIC.ACT_HI_VARINST where ID_ in ('-24', '-26');              

delete PUBLIC.ACT_HI_IDENTITYLINK where ID_  = '-30';       

delete PUBLIC.ACT_RU_IDENTITYLINK where ID_ ='-30'; 

delete PUBLIC.ACT_RU_VARIABLE where ID_ in ('-24', '-26');           

delete PUBLIC.ACT_RU_TASK where ID_ = '-29';  

delete PUBLIC.ACT_RU_EXECUTION where ID_ = '-22';  

delete PUBLIC.ACT_GE_BYTEARRAY where ID_ in ('-25', '-27');      