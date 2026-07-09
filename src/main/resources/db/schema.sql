create table if not exists sys_common_code_group (
    code_group varchar(50) primary key,
    code_group_name varchar(100) not null,
    description varchar(500),
    use_yn char(1) not null default 'Y',
    sort_order integer not null default 0,
    created_at timestamptz not null default now(),
    created_by varchar(50),
    updated_at timestamptz,
    updated_by varchar(50),
    constraint ck_sys_common_code_group_use_yn check (use_yn in ('Y', 'N'))
);

create table if not exists sys_common_code (
    code_group varchar(50) not null,
    code varchar(50) not null,
    code_name varchar(100) not null,
    description varchar(500),
    use_yn char(1) not null default 'Y',
    sort_order integer not null default 0,
    extra_value1 varchar(100),
    extra_value2 varchar(100),
    created_at timestamptz not null default now(),
    created_by varchar(50),
    updated_at timestamptz,
    updated_by varchar(50),
    primary key (code_group, code),
    constraint fk_sys_common_code_group
        foreign key (code_group)
        references sys_common_code_group (code_group),
    constraint ck_sys_common_code_use_yn check (use_yn in ('Y', 'N'))
);

create table if not exists sys_user (
    user_id varchar(50) primary key,
    username varchar(100) not null,
    password_hash varchar(255) not null,
    email varchar(255),
    use_yn char(1) not null default 'Y',
    locked_yn char(1) not null default 'N',
    last_login_at timestamptz,
    created_at timestamptz not null default now(),
    created_by varchar(50),
    updated_at timestamptz,
    updated_by varchar(50),
    constraint ck_sys_user_use_yn check (use_yn in ('Y', 'N')),
    constraint ck_sys_user_locked_yn check (locked_yn in ('Y', 'N'))
);

create table if not exists sys_role (
    role_code varchar(50) primary key,
    role_name varchar(100) not null,
    description varchar(500),
    use_yn char(1) not null default 'Y',
    created_at timestamptz not null default now(),
    created_by varchar(50),
    updated_at timestamptz,
    updated_by varchar(50),
    constraint ck_sys_role_use_yn check (use_yn in ('Y', 'N'))
);

create table if not exists sys_user_role (
    user_id varchar(50) not null,
    role_code varchar(50) not null,
    created_at timestamptz not null default now(),
    created_by varchar(50),
    primary key (user_id, role_code),
    constraint fk_sys_user_role_user
        foreign key (user_id)
        references sys_user (user_id),
    constraint fk_sys_user_role_role
        foreign key (role_code)
        references sys_role (role_code)
);

create table if not exists sys_menu (
    menu_code varchar(50) primary key,
    parent_menu_code varchar(50),
    menu_name varchar(100) not null,
    menu_path varchar(255),
    sort_order integer not null default 0,
    use_yn char(1) not null default 'Y',
    created_at timestamptz not null default now(),
    created_by varchar(50),
    updated_at timestamptz,
    updated_by varchar(50),
    constraint fk_sys_menu_parent
        foreign key (parent_menu_code)
        references sys_menu (menu_code),
    constraint ck_sys_menu_use_yn check (use_yn in ('Y', 'N'))
);

create table if not exists sys_role_menu (
    role_code varchar(50) not null,
    menu_code varchar(50) not null,
    can_read_yn char(1) not null default 'Y',
    can_create_yn char(1) not null default 'N',
    can_update_yn char(1) not null default 'N',
    can_delete_yn char(1) not null default 'N',
    created_at timestamptz not null default now(),
    created_by varchar(50),
    primary key (role_code, menu_code),
    constraint fk_sys_role_menu_role
        foreign key (role_code)
        references sys_role (role_code),
    constraint fk_sys_role_menu_menu
        foreign key (menu_code)
        references sys_menu (menu_code),
    constraint ck_sys_role_menu_read_yn check (can_read_yn in ('Y', 'N')),
    constraint ck_sys_role_menu_create_yn check (can_create_yn in ('Y', 'N')),
    constraint ck_sys_role_menu_update_yn check (can_update_yn in ('Y', 'N')),
    constraint ck_sys_role_menu_delete_yn check (can_delete_yn in ('Y', 'N'))
);

create table if not exists mst_item (
    item_code varchar(50) primary key,
    item_name varchar(200) not null,
    item_type_code varchar(50) not null,
    unit_code varchar(50) not null,
    spec varchar(200),
    use_yn char(1) not null default 'Y',
    created_at timestamptz not null default now(),
    created_by varchar(50),
    updated_at timestamptz,
    updated_by varchar(50),
    constraint ck_mst_item_use_yn check (use_yn in ('Y', 'N'))
);

