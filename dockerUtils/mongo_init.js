db.createUser(
    {
        user: 'mongo',
        pwd: 'mongo',
        roles: [
            {
                role: 'root',
                db: 'admin'
            }
        ]
    }
);