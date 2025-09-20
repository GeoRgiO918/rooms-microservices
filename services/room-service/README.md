# Room Service - API Reference

Room Service manages rooms. Provides REST API for CRUD operations.

## Endpoints
```bash
POST /rooms - create a room
GET /rooms - get all rooms
GET /rooms/{id} - get room by ID
PUT /rooms/{id} - update room by ID
DELETE /rooms/{id} - delete room by ID
```
## DTO example 

```json
{
  "id": 1,
  "number": "101",
  "description": "Spacious room with balcony",
  "type": "STANDARD",
  "personCount": 2,
  "version": 1
}