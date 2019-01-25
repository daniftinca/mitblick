INSERT INTO `roles`( `type`) VALUES ("ADMIN");
INSERT INTO `roles`( `type`) VALUES ("USER");

INSERT INTO `permissions`(`description`, `type`) VALUES ("User Management permission.","USER_MANAGEMENT");
INSERT INTO `permissions`(`description`, `type`) VALUES ("Skill Management permission.","SKILL_MANAGEMENT");
INSERT INTO `permissions`(`description`, `type`) VALUES ("Permission that enables the user to see all profiles and sort/filter through them.","PROFILE_HANDLING");


INSERT INTO `roles_permissions`(`Role_ID`, `permissions_ID`) VALUES (1,1);
INSERT INTO `roles_permissions`(`Role_ID`, `permissions_ID`) VALUES (1,2);
INSERT INTO `roles_permissions`(`Role_ID`, `permissions_ID`) VALUES (1,3);

INSERT INTO `skillarea`( `description`, `name`) VALUES ("Programming Skills","Programming");
INSERT INTO `skillarea`( `description`, `name`) VALUES ("Teaching Skills","Teaching");

INSERT INTO `skill`(`name`, `skillArea`) VALUES ("Java",1);
INSERT INTO `skill`(`name`, `skillArea`) VALUES ("Java",2);
INSERT INTO `skill`(`name`, `skillArea`) VALUES ("Python",1);
INSERT INTO `skill`(`name`, `skillArea`) VALUES ("Python",2);
INSERT INTO `skill`(`name`, `skillArea`) VALUES ("C",1);
INSERT INTO `skill`(`name`, `skillArea`) VALUES ("C++",1);

-- INSERT INTO `users_roles`(`User_ID`, `roles_ID`) VALUES (2,1);

INSERT INTO `region`(`ID`, `name`)
VALUES (1, 'Cluj-Napoca'),
       (2, 'Sibiu'),
       (3, 'Brasov'),
       (4, 'Timisoara')

       INSERT
INTO `jobtitle`(`ID`, `name`)
VALUES (1,
        'Junior Consultant'),(
        2,
        'Consultant'),(
        3,
        'Senior Consultant'),(
        4,
        'Manager'),(
        5,
        'Senior Manager'),(
        6,
        'Associated Partner'),(
        7,
        'Partner')