export class FileTreeNode {
  public path: string;
  public children: FileTreeNode[];
  public level: number;
  public expandable: boolean;
}
