CREATE TABLE sprints (
	sprint_id bigint PRIMARY KEY AUTO_INCREMENT,
	name varchar(50) NOT NULL UNIQUE,
	start_date date NOT NULL,
	end_date date NOT NULL,
	goal_description text NOT NULL, 
	status ENUM('Pending', 'In progress', 'Finished', 'Canceled') NOT NULL
);

CREATE TABLE user_stories(
	user_story_id bigint PRIMARY KEY AUTO_INCREMENT,
	description text NOT NULL,
	story_points int NOT NULL,
	attachments text array,
	status ENUM('To do', 'In progress', 'Review', 'Done') NOT NULL
);

CREATE TABLE user_stories_in_sprints(
	sprint_id bigint NOT NULL,
	user_story_id bigint NOT NULL,
	PRIMARY KEY(sprint_id, user_story_id),
	CONSTRAINT fk_sprint 
		FOREIGN KEY(sprint_id)
			REFERENCES sprints(sprint_id),
	CONSTRAINT fk_user_story 
		FOREIGN KEY(user_story_id)
			REFERENCES user_stories(user_story_id)
);