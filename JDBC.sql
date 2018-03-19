CREATE TABLE  writinggroup
(
    groupname varchar(255) NOT NULL,
    headwriter varchar(225),
    yearsformed varchar(4),
    subject varchar(225),
    CONSTRAINT pk_WritingGroup PRIMARY KEY (groupname)
);
/*------------------------------------------*/
CREATE TABLE  publisher
(
    publishername varchar(255) NOT NULL,
    publisherAddress varchar(225),
    PublisherPhone varchar(19),
    PublisherEmail varchar(225),
    CONSTRAINT pk_Publisher PRIMARY KEY (PublisherName)
);
CREATE TABLE  book
(
    booktitle varchar(255) NOT NULL,
    yearpublished varchar(4),
    numberpages varchar(10),
    groupname varchar(255)  NOT NULL,
    publishername varchar(255) NOT NULL,
    CONSTRAINT pk_Book PRIMARY KEY (groupname, booktitle)
);
alter table book
            add CONSTRAINT fk_BookID_WritingGroup 
            FOREIGN KEY (groupName) 
            REFERENCES writinggroup (groupName);
/*
    Inserting Groups
*/
insert into WritingGroup (GroupName,HeadWriter,YearsFormed, Subject)
values('FirstBlizzard','Caryl Wilkinson','2012','Action');
insert into WritingGroup (GroupName,HeadWriter,YearsFormed, Subject)
values('One','Lennard Garnett','2012','Action');
insert into WritingGroup (GroupName,HeadWriter,YearsFormed, Subject)
values('The Steel Resistance','Garret Matthewson','2015','Romance');
insert into WritingGroup (GroupName,HeadWriter,YearsFormed, Subject)
values('Hissing Spuds','Adriana Gibbs','2013','Science Fiction');
insert into WritingGroup (GroupName,HeadWriter,YearsFormed, Subject)
values('Atom Cats','Triston Kimball','2013','Horror');
insert into WritingGroup (GroupName,HeadWriter,YearsFormed, Subject)
values('The Paper Crew','Gabe Newell','2011','History');
insert into WritingGroup (GroupName,HeadWriter,YearsFormed, Subject)
values('Tunnel Snakes','Sharise Ericson','2011','Science Fiction');
/*
    Inserting Publishers
*/
insert into publisher (PublisherName,PublisherAddress,PublisherPhone,PublisherEmail)
values('Allen and Unwin','62 E. Alderwood Court East Stroudsburg, PA 18301','2025550143','contact@AllenAndUnwin.com');
insert into publisher (PublisherName,PublisherAddress,PublisherPhone,PublisherEmail)
values('Peace Hill Press','6 Glen Eagles St. Bedford, OH 44146','8035250175','contact@PeaceHillPress.com');
insert into publisher (PublisherName,PublisherAddress,PublisherPhone,PublisherEmail)
values('Orion Books','7097 Heather Road Rolla, MO 65401','2075050147','contact@orionBook.com');
insert into publisher (PublisherName,PublisherAddress,PublisherPhone,PublisherEmail)
values('Zed Books','8359 SW. Oxford Street Alabaster, AL 35007','3025350193','contact@zedBook.com');
insert into publisher (PublisherName,PublisherAddress,PublisherPhone,PublisherEmail)
values('UCL Press','8889 Circle Drive Natchez, MS 39120','2085450105','contact@uclpress.com');
insert into publisher (PublisherName,PublisherAddress,PublisherPhone,PublisherEmail)
values('Virago Press','9 West Boston Lane Redford, MI 48239','8435650182','contact@viragopress.com');
insert into publisher (PublisherName,PublisherAddress,PublisherPhone,PublisherEmail)
values('Crocker and Bewster','911 E. Bridle Road Edison, NJ 0881','3075350171','contact@crokerandbrewster.com');
insert into publisher (PublisherName,PublisherAddress,PublisherPhone,PublisherEmail)
values('Pan Books','9854 Blue Spring St. Montgomery, AL 36109','3345150163','contact@panBook.com');
insert into publisher (PublisherName,PublisherAddress,PublisherPhone,PublisherEmail)
values('Semprini Press','9642 Valley St. Tullahoma, TN 37388','5125550118','contact@semprinipress.com');
insert into publisher (PublisherName,PublisherAddress,PublisherPhone,PublisherEmail)
values('New American Library','1 Primrose Street Calumet City, IL 60409','4175550167','contact@newamericanlibrary.com');
insert into publisher (PublisherName,PublisherAddress,PublisherPhone,PublisherEmail)
values('Jump','322 Westport Ave. Los Angeles, CA 90008','7035550187','contact@jump.com');
 /*----------------------------------------
    Inserting Into Tables:
------------------------------------------*/
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('Destroying Blade', '1813', '1207', 'FirstBlizzard','Allen and Unwin' );
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('Iron of Darkness Club', '2016', '345', 'One', 'Peace Hill Press');
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('The Doom On the Hall', '1933','122','Tunnel Snakes', 'Orion Books');
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('Evil Chaining', '1933','323','One','Zed Book' );
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('The March of House', '1345','321','The Steel Resistance','Pan Books' );
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('Tarnished Incubuss', '1983','454','The Steel Resistance','UCL Press' );
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('Ashs Woman', '1933','123','Hissing Spuds','UCL Press' );
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('Lone Wanderer', '1988','678','Atom Cats','Pan Books' );
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('The Bronze of Mary', '1999','486','The Paper Crew','UCL Press');
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('Bladeborn', '1983','1000','Tunnel Snakes','New American Library' );
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('The ManGirl', '1933','234','Hissing Spuds','Crocker and Bewster');
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('Revolt', '1977' ,'3','Hissing Spuds','Virago Press');
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('The Home and the Machine', '1998','32','Hissing Spuds','Pan Books' );
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('The Dragging Express', '1987','323','The Steel Resistance','Semprini Press');
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('Combs Life', '1988','123','Hissing Spuds','Crocker and Bewster');
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('The Special Window Across the Zombie', '1993','532','Hissing Spuds','Crocker and Bewster' );
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('The Parlour-Study', '2003','189','The Paper Crew','Pan Books' );
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('Incubus Jungle', '1990' ,'5643','Hissing Spuds','Semprini Press');
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('Wind of City', '1966','3','The Paper Crew','New American Library');
insert into Book (BookTitle, YearPublished, NumberPages, GroupName, PublisherName)
values ('One Punch Man', '2015','552','One','Jump');