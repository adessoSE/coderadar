import {BufferGeometry, DefaultLoadingManager, Mesh, MeshBasicMaterial, Scene} from 'three';
import {GLTFLoader} from 'three/examples/jsm/loaders/GLTFLoader';
import {NodeType} from '../../enum/NodeType';

export class ChangetypeSymbols {

  private addedSymbol: Mesh;
  private deletedSymbol: Mesh;
  private modifiedSymbol: Mesh;
  private renamedSymbol: Mesh;

  constructor() {

  }

  async addChangeTypeSymbols(scene: Scene) {
    if (!(this.addedSymbol && this.deletedSymbol && this.modifiedSymbol && this.renamedSymbol)) {
      await this.loadResources();
    }

    scene.traverse(object => {
      let symbol: Mesh;
      if (object.userData) {
        if (object.userData.type === NodeType.FILE) {
          if (object.userData.changeTypes) {
            if (object.userData.changeTypes.added === true) {
              symbol = this.addedSymbol.clone();
            } else if (object.userData.changeTypes.deleted === true) {
              symbol = this.deletedSymbol.clone();
            } else if (object.userData.changeTypes.modified === true) {
              symbol = this.modifiedSymbol.clone();
            } else if (object.userData.changeTypes.renamed === true) {
              symbol = this.renamedSymbol.clone();
            }
          }
        }
      }
      if (symbol !== undefined) {
        symbol.scale.setScalar(0.5);
        symbol.position.set(0.5, 1, 0.5);
        object.add(symbol);
      }
    });
  }

  loadMaterial() {
    this.addedSymbol.material = new MeshBasicMaterial({
      color: 0xffff00
    });
    this.deletedSymbol.material = new MeshBasicMaterial({
      color: 0xff0000
    });
    this.modifiedSymbol.material = new MeshBasicMaterial({
      color: 0x0000ff
    });
    this.renamedSymbol.material = new MeshBasicMaterial({
      color: 0x00ff00
    });
  }

  loadModels(): Promise<any> {
    // Keep a reference to the class if the load callbacks finish after the method has finished
    const selfReference = this;
    const loader = new GLTFLoader(DefaultLoadingManager);
    return new Promise((resolve, reject) => {
      const onLoad = (gltf) => {
        gltf.scene.children.forEach(child => {
          const childMesh = child as Mesh;
          if (child.name.includes('added')) {
            selfReference.addedSymbol.geometry = childMesh.geometry as BufferGeometry;
          } else if (child.name.includes('deleted')) {
            selfReference.deletedSymbol.geometry = childMesh.geometry as BufferGeometry;
          } else if (child.name.includes('modified')) {
            selfReference.modifiedSymbol.geometry = childMesh.geometry as BufferGeometry;
          } else if (child.name.includes('renamed')) {
            selfReference.renamedSymbol.geometry = childMesh.geometry as BufferGeometry;
          }
        });
        resolve();
      };
      const onProgress = (xhr: ProgressEvent) => {};
      const onError = (error) => {
        console.error(error);
        reject();
      };
      loader.load('assets/changetype_symbols.gltf', onLoad, onProgress, onError);
      }
    );
  }

  loadResources(): Promise<any> {
    this.addedSymbol = new Mesh();
    this.deletedSymbol = new Mesh();
    this.modifiedSymbol = new Mesh();
    this.renamedSymbol = new Mesh();
    return Promise.all([this.loadMaterial(), this.loadModels()]);
  }
}
