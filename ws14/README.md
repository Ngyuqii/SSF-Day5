## Workshop 14
- To configure a key/value data source and to persist data to this data source.
- Use dependency injection to provide persistence service to controllers and other components.

### Task 1
Connect to remote database (redis-cli).

### Task 2
Configure and create a RedisTemplate.

### Task 3
1. Create a bean called ContactsRedis.
2. Inject the RedisTemplate into ContactsRedist. <br>
ContactsRedis class should provide the same methods as Contacts from persisting and querying data ContactRedis stores the contact information on the remote database instead of the local file system.

### Task 4
Write a test to test ContactsRedis bean.

### Task 5
Integrate ContactRedis into the contact controller (the controller that /contact resources maps to). The controller should now read and write contacts from Redis instead of the local file system.

### Task 6
Deploy to Railway.

