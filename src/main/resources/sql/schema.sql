CREATE TABLE DOMAINEVENTENTRY (
  GLOBALINDEX         NUMERIC(20, 0) NOT NULL GENERATED ALWAYS AS IDENTITY,
  AGGREGATEIDENTIFIER VARCHAR(255)   NOT NULL,
  SEQUENCENUMBER      BIGINT         NOT NULL,
  TYPE                VARCHAR(255)   NOT NULL,
  EVENTIDENTIFIER     VARCHAR(255)   NOT NULL,
  METADATA            BLOB,
  PAYLOAD             BLOB           NOT NULL,
  PAYLOADREVISION     VARCHAR(255),
  PAYLOADTYPE         VARCHAR(255)   NOT NULL,
  TIMESTAMP           VARCHAR(255)   NOT NULL,
  CONSTRAINT PK_DOMAINEVENTENTRY_GLOBAL_INDEX PRIMARY KEY (GLOBALINDEX),
  CONSTRAINT UC_DOMAIN_EVENT_ENTRY_ID_SEQUENCE UNIQUE (AGGREGATEIDENTIFIER, SEQUENCENUMBER),
  CONSTRAINT UC_DOMAIN_EVENT_ENTRY_EVENT_ID UNIQUE (EVENTIDENTIFIER)
)
GO

CREATE TABLE SNAPSHOTEVENTENTRY (
  AGGREGATEIDENTIFIER VARCHAR(255) NOT NULL,
  SEQUENCENUMBER      BIGINT       NOT NULL,
  TYPE                VARCHAR(255) NOT NULL,
  EVENTIDENTIFIER     VARCHAR(255) NOT NULL,
  METADATA            BLOB,
  PAYLOAD             BLOB         NOT NULL,
  PAYLOADREVISION     VARCHAR(255),
  PAYLOADTYPE         VARCHAR(255) NOT NULL,
  TIMESTAMP           VARCHAR(255) NOT NULL,
  CONSTRAINT PK_SNAPSHOT_EVENT_ENTRY PRIMARY KEY (AGGREGATEIDENTIFIER, SEQUENCENUMBER),
  CONSTRAINT UC_SNAPSHOT_EVENT_ENTRY_EVENT_IDENTIFIER UNIQUE (EVENTIDENTIFIER)
)
GO


CREATE TABLE SAGAENTRY (
                           SAGAID VARCHAR(255) not null constraint PK_SAGA_ENTRY primary key,
                           REVISION VARCHAR(255),
                           SAGATYPE VARCHAR(255),
                           SERIALIZEDSAGA BLOB(1048576)
)
GO

CREATE TABLE TOKENENTRY (
                            PROCESSORNAME VARCHAR(255) not null,
                            SEGMENT INTEGER not null,
                            TOKEN BLOB(10000),
                            TOKENTYPE VARCHAR(255),
                            TIMESTAMP VARCHAR(255) not null,
                            OWNER VARCHAR(255),
                            constraint PK_TOKEN_ENTRY
                                primary key (PROCESSORNAME, SEGMENT)
)
GO

CREATE TABLE ASSOCIATIONVALUEENTRY (
                                       ID NUMERIC(20, 0) NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                       ASSOCIATIONKEY VARCHAR(255),
                                       ASSOCIATIONVALUE VARCHAR(255),
                                       SAGAID VARCHAR(255),
                                       SAGATYPE VARCHAR(255)
)
GO

