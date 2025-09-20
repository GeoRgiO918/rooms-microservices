# File Service

## Endpoints
```text
POST   /file/upload          - upload a file with metadata
GET    /file                 - get all file metadata (optional status filter)
GET    /file/{id}            - get file metadata by ID
GET    /file/{id}/download   - download file by ID
DELETE /file/{id}            - soft delete file by ID