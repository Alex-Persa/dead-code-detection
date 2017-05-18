# dead-code-detection

# Overview: 
Detects dead code in any public github repository. By definition, dead code is "A variable, parameter, field, method or class is no longer used (usually because it is obsolete)". 

Expose REST endpoints to:
Add a github repository - Adding a repository automatically triggers the algorithm to detect dead code in that repository.
List all repositories that have already been added along with their status:
a. All reposhave an ID assigned automatically
b. Their current status is returned: 
=> ADDED (didn't started processing yet – this should fairly quick)
=> PROCESSING (processing is in progress)
=> COMPLETED (successfully) 
=> FAILED (some error has occurred, exceptions as well)
c. Timestamps like the time when the repo was added and when the processing finished, is also returned. 
Get dead code occurrences for a given github repository:
a. Private functions that are never being used by any part of the codebase
b. Local/Global private variables that are not being used
c. Function parameters that are not being used within the function
