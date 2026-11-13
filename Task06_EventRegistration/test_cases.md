# Task 06 Test Cases

| Case | Expected result |
| --- | --- |
| Valid registration | Successful insert |
| Empty register number | Validation error |
| Empty name | Validation error |
| Missing event | Validation error |
| Duplicate registration | Database constraint prevents duplicate |
| Filter by event | Matching rows only |
| CSV export | File created |
| Database down | Connection error message |
