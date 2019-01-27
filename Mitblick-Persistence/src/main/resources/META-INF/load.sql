INSERT INTO `roles`( `type`) VALUES ("ADMIN");
INSERT INTO `roles`( `type`) VALUES ("EMPLOYEE");
INSERT INTO `roles`( `type`) VALUES ("SUPERIOR");
INSERT INTO `roles`( `type`) VALUES ("SUPERVISOR");

INSERT INTO `permissions`(`description`, `type`) VALUES ("User Management permission.","USER_MANAGEMENT");
INSERT INTO `permissions`(`description`, `type`) VALUES ("Skill Management permission.","SKILL_MANAGEMENT");
INSERT INTO `permissions`(`description`, `type`) VALUES ("Permission that enables the user to see all profiles and sort/filter through them.","PROFILE_HANDLING");
INSERT INTO `permissions`(`description`, `type`) VALUES ("Profile review permission.","PROFILE_REVIEW");

INSERT INTO `roles_permissions`(`Role_ID`, `permissions_ID`) VALUES (1,1);
INSERT INTO `roles_permissions`(`Role_ID`, `permissions_ID`) VALUES (1,2);
INSERT INTO `roles_permissions`(`Role_ID`, `permissions_ID`) VALUES (1,3);
INSERT INTO `roles_permissions`(`Role_ID`, `permissions_ID`) VALUES (1,4);
INSERT INTO `roles_permissions`(`Role_ID`, `permissions_ID`) VALUES (4,4);
INSERT INTO `roles_permissions`(`Role_ID`, `permissions_ID`) VALUES (3,3);

INSERT INTO `skillarea`( `description`, `name`) VALUES ("Programming Skills","Programming");
INSERT INTO `skillarea`( `description`, `name`) VALUES ("Teaching Skills","Teaching");

INSERT INTO `skill`(`name`, `skillArea`) VALUES ("Java",1);
INSERT INTO `skill`(`name`, `skillArea`) VALUES ("Java",2);
INSERT INTO `skill`(`name`, `skillArea`) VALUES ("Python",1);
INSERT INTO `skill`(`name`, `skillArea`) VALUES ("Python",2);
INSERT INTO `skill`(`name`, `skillArea`) VALUES ("C",1);
INSERT INTO `skill`(`name`, `skillArea`) VALUES ("C++",1);


INSERT INTO `region`(`name`) VALUES ('Cluj-Napoca'),('Sibiu'),('Brasov'),('Timisoara');

INSERT INTO `jobtitle`(`name`) VALUES ('Junior Consultant'),('Consultant'), ('Senior Consultant'), ('Manager'), ('Senior Manager'), ('Associated Partner'), ('Partner');



INSERT INTO `users`( `email`, `FAILEDATTEMPTS`, `isActive`, `password`) VALUES ('admin@mitblick.com',0,true,"¦ù9§Èç‚x“ùqÔäH-‰");
INSERT INTO `profile`( `email`, `firstName`, `isAccepted`, `lastName`, `photo`) VALUES ("admin@mitblick.com","First",false,"Last","");

INSERT INTO `users`( `email`, `FAILEDATTEMPTS`, `isActive`, `password`) VALUES ('user1@mitblick.com',0,true,"¦ù9§Èç‚x“ùqÔäH-‰");
INSERT INTO `profile`( `email`, `firstName`, `isAccepted`, `lastName`, `photo`) VALUES ("user1@mitblick.com","First",false,"Last","");

INSERT INTO `users`( `email`, `FAILEDATTEMPTS`, `isActive`, `password`) VALUES ('user2@mitblick.com',0,true,"¦ù9§Èç‚x“ùqÔäH-‰");
INSERT INTO `profile`( `email`, `firstName`, `isAccepted`, `lastName`, `photo`) VALUES ("user2@mitblick.com","First",false,"Last","");

INSERT INTO `users`( `email`, `FAILEDATTEMPTS`, `isActive`, `password`) VALUES ('superior@mitblick.com',0,true,"¦ù9§Èç‚x“ùqÔäH-‰");
INSERT INTO `profile`( `email`, `firstName`, `isAccepted`, `lastName`, `photo`) VALUES ("superior@mitblick.com","First",false,"Last","");

INSERT INTO `users`( `email`, `FAILEDATTEMPTS`, `isActive`, `password`) VALUES ('supervisor1@mitblick.com',0,true,"¦ù9§Èç‚x“ùqÔäH-‰");
INSERT INTO `profile`( `email`, `firstName`, `isAccepted`, `lastName`, `photo`) VALUES ("supervisor1@mitblick.com","First",false,"Last","");

INSERT INTO `users`( `email`, `FAILEDATTEMPTS`, `isActive`, `password`) VALUES ('supervisor2@mitblick.com',0,true,"¦ù9§Èç‚x“ùqÔäH-‰");
INSERT INTO `profile`( `email`, `firstName`, `isAccepted`, `lastName`, `photo`) VALUES ("supervisor2@mitblick.com","First",false,"Last","");

INSERT INTO `users_roles`(`User_ID`, `roles_ID`) VALUES (1,1);
INSERT INTO `users_roles`(`User_ID`, `roles_ID`) VALUES (2,2);
INSERT INTO `users_roles`(`User_ID`, `roles_ID`) VALUES (3,2);
INSERT INTO `users_roles`(`User_ID`, `roles_ID`) VALUES (4,3);
INSERT INTO `users_roles`(`User_ID`, `roles_ID`) VALUES (5,4);
INSERT INTO `users_roles`(`User_ID`, `roles_ID`) VALUES (6,4);

