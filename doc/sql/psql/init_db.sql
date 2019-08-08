
create database toy;

create user jwtmanager with encrypted password 'jwtmanager';
create schema jwt;

GRANT CONNECT ON DATABASE toy TO jwt;
alter schema jwt owner to jwtmanager;

select current_schema();
show search_path();

alter role camanager set search_path to jwt

-- 사용자 저장 테이블
create table user_info (
	id character varying(24), -- 사용자 ID
	name character varying(16), -- 사용자 이름
	password character varying(512), -- hashed 비밀번호	
	auth_state integer, --0: 최고관리자, 1: 유저
	primary key (id)
);

CREATE SEQUENCE keypair_info_seq START 1;
create table key_pair_info(
    seq_id integer default nextval('keypair_info_seq'),
    pri_key bytea,
    cert bytea,
    usage boolean,
    kid character varying(40)
);
