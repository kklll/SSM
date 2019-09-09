-- create database chapter22;
--
-- use chapter22;
/*==============================================================*/
/* Table: �����                                        */
/*==============================================================*/
create table T_RED_PACKET
(
   id                   int(12)                        not null auto_increment,
   user_id              int(12)                        not null,
   amount               decimal(16,2)                  not null,
   send_date            timestamp                      not null,
   total                int(12)                        not null,
   unit_amount          decimal(12)                    not null,
   stock                int(12)                        not null,
   version              int(12) default 0              not null,
   note                 varchar(256)                    null,
   primary key clustered (id)
);

/*==============================================================*/
/* Table: �û��������                                                */
/*==============================================================*/
create table T_USER_RED_PACKET 
(
   id                   int(12)                        not null auto_increment,
   red_packet_id        int(12)                        not null,
   user_id              int(12)                        not null,
   amount               decimal(16,2)                  not null,
   grab_time            timestamp                      not null,
   note                 varchar(256)                   null,
    primary key clustered (id)
);




/**
* ����һ��20��Ԫ��2���С�����ÿ��10Ԫ�ĺ������
*/
insert into T_RED_PACKET(user_id, amount, send_date, total, unit_amount, stock, note)
 values(1, 200000.00, now(), 20000, 10.00, 20000,'20万元金额，2万个红包，每个10元');