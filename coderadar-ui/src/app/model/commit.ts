export class Commit {
  name: string;
  author: string;
  comment: string;
  timestamp: number;
  analyzed: boolean;

  constructor(commit?: any) {
    if (commit !== undefined && commit !== null) {
      this.name = commit.name;
      this.author = commit.author;
      this.comment = commit.comment;
      this.timestamp = commit.timestamp;
      this.analyzed = commit.analyzed;
    }
  }
}
