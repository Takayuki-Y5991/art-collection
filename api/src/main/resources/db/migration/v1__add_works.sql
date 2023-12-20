CREATE TABLE IF NOT EXISTS works (
    id UUID PRIMARY KEY NOT NULL default gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    creator VARCHAR(255) NOT NULL,
    description VARCHAR(255)
    is_publish boolean NOT NULL default true,
    is_ai_learning boolean NOT NULL default false,
    created_at TIMESTAMP NOT NULL default now()
);

CREATE TABLE IF NOT EXISTS work_tags (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tag_group (
   work_id UUID NOT NULL,
   work_tag INTEGER NOT NULL,
   FOREIGN KEY (work_id) REFERENCES works(id),
   FOREIGN KEY (work_tag) REFERENCES work_tags(id),
   PRIMARY KEY (work_id, work_tag)
);

COMMENT ON COLUMN works.is_ai_learning IS 'AI生成の素材使用可否について';