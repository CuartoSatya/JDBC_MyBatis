```mermaid
sequenceDiagram
Controller->>Service: request
Service->>Repository: query
Repository-->>Service: result
Service-->>Controller: response
