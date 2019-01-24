INSERT INTO `roles`( `type`) VALUES ("ADMIN");
INSERT INTO `roles`( `type`) VALUES ("USER");

INSERT INTO `permissions`(`description`, `type`) VALUES ("User Management permission.","USER_MANAGEMENT");
INSERT INTO `permissions`(`description`, `type`) VALUES ("Skill Management permission.","SKILL_MANAGEMENT");
INSERT INTO `permissions`(`description`, `type`) VALUES ("Permission that enables the user to see all profiles and sort/filter through them.","PROFILE_HANDLING");


INSERT INTO `roles_permissions`(`Role_ID`, `permissions_ID`) VALUES (1,1);
INSERT INTO `roles_permissions`(`Role_ID`, `permissions_ID`) VALUES (1,2);
INSERT INTO `roles_permissions`(`Role_ID`, `permissions_ID`) VALUES (1,3);

INSERT INTO `users_roles`(`User_ID`, `roles_ID`) VALUES (2,1);