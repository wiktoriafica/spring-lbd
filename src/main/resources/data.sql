INSERT INTO sprints(name, start_date, end_date, goal_description, status) VALUES
	('Server', '2022-05-21', '2022-06-15', 'To finish all tasks', 'Finished');
SET @sprint_id = SELECT max(sprint_id) FROM sprints;
INSERT INTO user_stories(name, description, story_points, attachments, status) VALUES
	('Task1', 'Added first task', 10, ARRAY['/binary.txt'], 'TO_DO');
SET @user_story_id = SELECT max(user_story_id) FROM user_stories;
INSERT INTO user_stories_in_sprints VALUES(@sprint_id, @user_story_id);

INSERT INTO user_stories(name, description, story_points, attachments, status) VALUES
	('Task2', 'Added second task', 3, ARRAY['/binary.txt'], 'IN_PROGRESS');
SET @user_story_id = SELECT max(user_story_id) FROM user_stories;
INSERT INTO user_stories_in_sprints VALUES(@sprint_id, @user_story_id);

INSERT INTO user_stories(name, description, story_points, attachments, status) VALUES
	('Task3', 'Added third task', 8, ARRAY['/binary.txt'], 'REVIEW');
SET @user_story_id = SELECT max(user_story_id) FROM user_stories;
INSERT INTO user_stories_in_sprints VALUES(@sprint_id, @user_story_id);

INSERT INTO user_stories(name, description, story_points, attachments, status) VALUES
	('Task4', 'Added fourth task', 15, ARRAY['/binary.txt'], 'DONE');
SET @user_story_id = SELECT max(user_story_id) FROM user_stories;
INSERT INTO user_stories_in_sprints VALUES(@sprint_id, @user_story_id);