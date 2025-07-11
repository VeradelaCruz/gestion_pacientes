
    create table doctors (
        friday bit,
        monday bit,
        saturday bit,
        sunday bit,
        thursday bit,
        tuesday bit,
        wednesday bit,
        id_doctor bigint not null auto_increment,
        licence_number varchar(15),
        phone_number varchar(15),
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        specialty varchar(255),
        primary key (id_doctor)
    ) engine=InnoDB;
