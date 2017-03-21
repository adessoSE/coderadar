/**
 * Removes all results from AssociateGitLogJobs from the database.
 */
delete from commit_file;
delete from file;
delete from file_identity;
delete from job where job_type in ('ScanFilesJob', 'AnalyzeCommitJob', 'AssociateGitLogJob');
update commit set merged = false, scanned = true, analyzed = false;