DROP TABLE IF EXISTS user_stories_in_sprints;
DROP TABLE IF EXISTS sprints;
DROP TABLE IF EXISTS user_stories;

CREATE TABLE IF NOT EXISTS sprints (
	sprint_id bigint PRIMARY KEY AUTO_INCREMENT,
	name varchar(50) NOT NULL UNIQUE,
	start_date date NOT NULL,
	end_date date NOT NULL,
	goal_description text, 
	status ENUM('PENDING', 'IN_PROGRESS', 'FINISHED', 'CANCELED') NOT NULL
);

CREATE TABLE IF NOT EXISTS user_stories(
	user_story_id bigint PRIMARY KEY AUTO_INCREMENT,
	name varchar(50) NOT NULL UNIQUE,
	description text NOT NULL,
	story_points int,
	attachments text array,
	status ENUM('TO_DO', 'IN_PROGRESS', 'REVIEW', 'DONE') NOT NULL
);

CREATE TABLE IF NOT EXISTS user_stories_in_sprints(
	sprint_id bigint NOT NULL,
	user_story_id bigint NOT NULL,
	PRIMARY KEY(sprint_id, user_story_id),
	CONSTRAINT fk_sprint 
		FOREIGN KEY(sprint_id)
			REFERENCES sprints(sprint_id)
                ON DELETE CASCADE,
	CONSTRAINT fk_user_story 
		FOREIGN KEY(user_story_id)
			REFERENCES user_stories(user_story_id)
                ON DELETE CASCADE
);