import {BufferGeometry, DefaultLoadingManager, LoadingManager, Mesh, MeshBasicMaterial, Scene} from "three";
import {GLTFLoader} from "three/examples/jsm/loaders/GLTFLoader";
import {NodeType} from "../../enum/NodeType";

export class ChangetypeSymbols{

  private addedGeometry:BufferGeometry;
  private removedGeometry:BufferGeometry;
  private modifiedGeometry:BufferGeometry;
  private renamedGeometry:BufferGeometry;

  private addedMaterial:MeshBasicMaterial;
  private removedMaterial:MeshBasicMaterial;
  private modifiedMaterial:MeshBasicMaterial;
  private renamedMaterial:MeshBasicMaterial;

  constructor() {
    this.loadResources();
  }

  addChangetypeSymbols(scene:Scene){
    scene.traverse(object => {
      let symbol:Mesh = undefined;
      if(object.userData){
        if(object.userData.type === NodeType.FILE){
          if(object.userData.changeTypes){
            if(object.userData.changeTypes.added === true){
              symbol = new Mesh(this.addedGeometry,this.addedMaterial);
              console.log("added symbol");
            }else if(object.userData.changeTypes.removed === true){
              symbol = new Mesh(this.removedGeometry,this.removedMaterial);
              console.log("removed symbol");
            }else if(object.userData.changeTypes.modified === true){
              symbol = new Mesh(this.modifiedGeometry,this.modifiedMaterial);
              console.log("modified symbol");
            }else if(object.userData.changeTypes.renamed === true){
              symbol = new Mesh(this.renamedGeometry,this.renamedMaterial);
              console.log("renamed symbol");
            }
          }
        }
      }
      if(symbol!==undefined){
        console.log("symbol added to "+object.name)
        object.add(symbol);
        symbol.scale.setScalar(0.5)
        symbol.position.set(0.5,1,0.5);
        console.log(symbol.scale)
      }
    })
  }

  loadMaterial(){
      this.addedMaterial = new MeshBasicMaterial({
        color: 0xffff00
      });
      this.removedMaterial = new MeshBasicMaterial({
        color: 0xff0000
      });
      this.modifiedMaterial = new MeshBasicMaterial({
        color: 0x0000ff
      });
      this.renamedMaterial = new MeshBasicMaterial({
        color: 0x00ff00
      });
  }

  async loadModels(){
    //Keep a reference to the class if the load callbacks finish after the method has finished
    let selfReference = this;
    let loader = new GLTFLoader(DefaultLoadingManager);
    loader.load(
      'assets/changetype_symbols.gltf',
      function (gltf) {
        console.log(gltf);
        gltf.scene.children.forEach(child => {
          let childMesh = <Mesh>child;
          if (child.name.includes('added')) {
            selfReference.addedGeometry = <BufferGeometry>childMesh.geometry;
          } else if (child.name.includes('removed')) {
            selfReference.removedGeometry = <BufferGeometry>childMesh.geometry;
          } else if (child.name.includes('modified')) {
            selfReference.modifiedGeometry = <BufferGeometry>childMesh.geometry;
          } else if (child.name.includes('renamed')) {
            selfReference.renamedGeometry = <BufferGeometry>childMesh.geometry;
          }
        })
      },
      function (xhr) {
        console.log((xhr.loaded / xhr.total * 100) + '% loaded');
      },
      function (error) {
        console.error(error);
      }
    )
  }

  loadResources(){
    this.loadMaterial()
    this.loadModels()
  }
}
