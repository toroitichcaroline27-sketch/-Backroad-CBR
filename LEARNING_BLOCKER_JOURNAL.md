# Learning & Blocker Journal: Northstar Retail Co. Inventory Sync

| Blocker ID | Phase | What I Was Trying To Do | Command Entered | Error Received | What I Learned | Resolution | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **1** | Java Setup | Configure Java environment variable | `set JAVA-HOME environment variable` | `Set-Variable : A positional parameter cannot be found...` | PowerShell requires exact variable syntax (`$env:JAVA_HOME` or System Properties). | Configured `JAVA_HOME` in System Properties. | Resolved |
| **2** | Java Setup | Verify Java installation | `java -version` | `java : The term 'java' is not recognized...` | Newly installed PATH variables require a terminal restart to take effect. | Restarted terminal session to reload PATH. | Resolved |
| **3** | Java Setup | Confirm JDK compiler availability | `javac -version` | None (`javac 21.0.12`) | Verified full JDK installation with compiler present. | Confirmed successful JDK setup. | Resolved |
| **4** | Maven Check | Verify Maven CLI command | `mvn-version` | `mvn-version : The term 'mvn-version' is not recognized...` | Command flags require space separation (`mvn -version`). | Re-ran command as `mvn -version`. | Resolved |
| **5** | Build / Maven | Clean build project workspace | `mvn clean compile` | `[ERROR] Failed to execute goal ... Failed to delete target\classes` | File lock held on `target/classes` directory by background Java process. | Ran `Stop-Process -Name "java" -Force` and recompiled. | Resolved |
| **6** | Git Commit | Record feature commit in Git | `git commit -m "..."` | `create mode 100644 tGet-Content...` | Misplaced terminal paste created an unwanted file on disk. | Removed file with `git rm` and cleaned commit history. | Resolved |
| **7** | Webhook Test | Test blocker resolution endpoint | `Invoke-RestMethod .../resolve -Method Put` | `(404) Not Found` | Unsaved syntax errors in controller prevented endpoint route registration. | Fixed syntax errors, recompiled, and restarted server. | Resolved |
| **8** | Webhook Sync | Implement automatic blocker resolution | N/A (Feature Implementation) | N/A | Restock events (`quantity > 0`) trigger custom JPA queries to clear open blockers. | Added auto-resolution logic to sync open blockers. | Resolved |
