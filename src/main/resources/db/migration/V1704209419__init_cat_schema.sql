 CREATE TABLE IF NOT EXISTS cat (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL
 );
--
-- 如果想加/减column改这个schema，不可以直接在这个文件上改，
-- 需要新建一个新的flyway schema文件，然后在新的schema文件上改，然后执行mvn flyway:migrate