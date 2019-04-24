export class Node {
  public filename: string;
  public path: string;
  public packageName: string;
  public children: Node[];
  public dependencies: Node[];
  public expanded: boolean;
}
