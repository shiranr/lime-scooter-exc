#lime-scooter-exc

##Introduction
This is an exercise for the CSE group. 
We will have an application for a scooter company.\
The application will allow you to get a list of all company scooters, 
select one for a ride and checkout the scooter once you are done. 

##API Endpoints
###Available endpoints
####Get Scooters
Returns all company scooters.\
Each scooter has a unique ID which you can use to query its data, battery percentage and location. 
```
GET /api/scooters 
```
```
200 OK 
```
```json
{
  "scooters": [
      {
        "id": "507f1f77bcf86cd799439011",
        "battery_percentage": 12,
        "lat": 34.787948,
        "lng": 32.114154
      },
      {
        "id": "507f191e810c19729de860ea",
        "battery_percentage": 90,
        "lat": 34.787948,
        "lng": 32.114154
      }
  ]
}
```

---

####Get scooter
Get a specific scooter data containing the status of the scooter is it already checked or available:
```
GET /api/scooter/{id}
```
Response:
```
200 OK 
```
```json
{
    "id": "507f1f77bcf86cd799439011",
    "battery_percentage": 12,
    "lat": 34.787948,
    "lng": 32.114154,
    "checked": true
 }
```
<span style="color:red">Errors:</span>

Scooter does not exist:
```
400 BAD REQUEST 
```
---

####Check in scooter
 
```
POST /api/scooter/{id}/checkin
```
Response:
```
200 OK 
```
<span style="color:red">Errors:</span>

Scooter already checked in:
```
409 CONFLICT 
``` 
Scooter does not exist:
```
400 BAD REQUEST 
```

---

####Check out scooter
```
POST /api/scooter/{id}/checkout 
```
Response: 
```
200 OK 
```

<span style="color:red">Errors:</span>

Scooter already checked out:
```
409 CONFLICT 
``` 
Scooter does not exist:
```
400 BAD REQUEST 
```


---
###Admin 
####Scooter history
```
GET /api/scooter/{id}/history
```
Response:
```
200 OK 
```
```json
{
    "id": "507f1f77bcf86cd799439011",
    "battery_percentage": 12,
    "lat": 34.787948,
    "lng": 32.114154,
    "checked": true,
    "history": [
      {
        "checked_in_at": 1607526588715,
        "checked_out_at": 1607526610289
      }
    ]
 }
```
Scooter does not exist:
```
400 BAD REQUEST 
```