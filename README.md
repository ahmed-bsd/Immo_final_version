The end points are configured based on roles, your controllers should be:
+ for admin
```
@RequestMapping( "api/dashboard/")

```


+ for seller
```
@RequestMapping( "api/user/seller/")

```

+ for client
```
@RequestMapping( "api/user/client/")

```

+ for visitor ( not authenticated)

```
@RequestMapping( "api/public/")

```