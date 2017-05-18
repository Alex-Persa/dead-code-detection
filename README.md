# dead-code-detection

## Overview: 
<p>Detects dead code in any public github repository. By definition, dead code is "A variable, parameter, field, method or class is no longer used (usually because it is obsolete)". 

<p> <b>Expose REST endpoints to: </b>
<p> <b>- Add a github repository - Adding a repository automatically triggers the algorithm to detect dead code in that repository.</b>
<p> <b>- List all repositories that have already been added along with their status: </b>
<p>a. All repos have an ID assigned automatically
<p>b. Their current status is returned: 
<p>=> ADDED (didn't started processing yet – this should fairly quick)
<p>=> PROCESSING (processing is in progress)
<p>=> COMPLETED (successfully) 
<p>=> FAILED (some error has occurred, exceptions as well)
<p>c. Timestamps like the time when the repo was added and when the processing finished, is also returned. 
<p> <b>- Get dead code occurrences for a given github repository:</b>
<p>a. Private functions that are never being used by any part of the codebase
<p>b. Local/Global private variables that are not being used
<p>c. Function parameters that are not being used within the function

## Prerequirments for running locally
<p> Install scitools Understand https://scitools.com/
<p> setup the license to scitools
<p> Set the path in dead-code-detection/src/main/resources/application.yml to the und executable from SciTools/bin  
<p> java 8 installed
<p> If running from ideea intellij add lombok plugin -> https://plugins.jetbrains.com/plugin/6317-lombok-plugin



