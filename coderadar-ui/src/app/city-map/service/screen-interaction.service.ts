import {Injectable} from '@angular/core';
import {ScreenComponent} from "../visualization/screen/screen.component";
import {ScreenType} from "../enum/ScreenType";
import {Object3D} from "three";
import {Subject} from "rxjs";

@Injectable()
export class ScreenInteractionService {
  rightScreen:ScreenComponent;
  leftScreen:ScreenComponent;




  private hoverHighlight:string;
  private highlightedElements: string[] = [];

  private highlightedElementsSubject = new Subject<string[]>();
  public highlightedElements$ = this.highlightedElementsSubject.asObservable();


  public setMouseHighlight(elementName:string){
    if(this.hoverHighlight == elementName)return;
    var isFile = elementName.includes(".");
    var exists = elementName != "";
    if(exists){
      if(isFile){
        this.hoverHighlight = elementName;
      }else{
        this.hoverHighlight = "";
      }
    }else{
      this.hoverHighlight = "";
    }
    this.emitHighlights();
  }

  public select(elementName:string){
    this.highlightedElements = [elementName];
    this.emitHighlights();
  }

  public toggleSelect(elementName:string){
    var index = this.highlightedElements.indexOf(elementName);
    console.log(index);
    if(index === -1){
      this.highlightedElements.push(elementName);
    } else{
      this.highlightedElements.splice(index,1);
    }
    this.emitHighlights();
  }

  public unselect(elementName:string){
    var index = this.highlightedElements.indexOf(elementName);
    this.highlightedElements.splice(index,1);
    this.emitHighlights();
  }

  public resetSelection(){
    console.log("Reset");
    this.highlightedElements = [];
    this.emitHighlights();
  }

  public getSelectedElementNames():string[]{
    return this.highlightedElements;
  }

  private emitHighlights(){
    var emittedHighlights:string[] = [];
    emittedHighlights = emittedHighlights.concat(this.highlightedElements);
    if(this.hoverHighlight != ""&&emittedHighlights.indexOf(this.hoverHighlight)<0)emittedHighlights.push(this.hoverHighlight);
    this.highlightedElementsSubject.next(emittedHighlights);
  }

  constructor() {

  }

  public otherScreen(screen:ScreenComponent): ScreenComponent{
    if(screen.screenType==ScreenType.LEFT)return this.rightScreen;
    else if(screen.screenType==ScreenType.RIGHT)return this.leftScreen;
    else return null;
  }

  public addScreen(screen:ScreenComponent) {
    if (screen.screenType == ScreenType.RIGHT) {
      this.rightScreen = screen;
    } else if (screen.screenType == ScreenType.LEFT) {
      this.leftScreen = screen;
    }
  }

  public getBlocksOfName(name:string):{rightBlock:Object3D,leftBlock:Object3D}{
    var rightBlock;
    var leftBlock;
    if(this.rightScreen)rightBlock=this.rightScreen.scene.getObjectByName(name);
    if(this.leftScreen)leftBlock=this.leftScreen.scene.getObjectByName(name);
    return {rightBlock,leftBlock};
  }

  getCounterpart(block:Object3D):Object3D{
    var pair = this.getBlocksOfName(block.name);
    if(pair.leftBlock==block)return pair.rightBlock;
    else if(pair.rightBlock==block)return pair.leftBlock;
    else return null;
  }
}
