create table if not exists post (
    id serial not null,
    creationDate date not null,
    title char not null,
    textBody char,
    img char,
    author char,
    likes int,
    primary key (id)
);