import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Character } from '../model/Character';
import { MarvelserviceService } from '../service/marvelservice.service';

@Component({
  selector: 'app-searchresult',
  templateUrl: './searchresult.component.html',
  styleUrls: ['./searchresult.component.css']
})
export class SearchresultComponent implements OnInit, OnDestroy{

  searchPhrase=''
  param$!: Subscription
  characters!: Character[]
  nextPage!: Character[]
  previousPage!: Character[]
  offset=0
  nextButton: boolean = true
  previousButton: boolean = false

  constructor(private activatedRoute: ActivatedRoute, private router: Router,
            private marvelService: MarvelserviceService){

            }

  ngOnInit(): void {
      this.param$= this.activatedRoute.params.subscribe(
        async (params)=> {
          this.searchPhrase=params['searchphrase']
          const l = await this.marvelService.getCharacters(this.searchPhrase, 10, this.offset)
          if (l.length == 0 || l==undefined){
            this.router.navigate(['/'])
          } else {
            this.characters=l
          }
          const n = await this.marvelService.getCharacters(this.searchPhrase, 10, this.offset + 10)
          if (n.length == 0){
            this.nextButton = false
          } else {
            this.nextPage=n
          }
        }
      )
      console.log(this.characters)
    }

  ngOnDestroy(): void {
      this.param$.unsubscribe()
  }

  async previous(){
    this.nextButton = true
    this.nextPage = this.characters
    this.characters = this.previousPage
    this.offset-=10
    console.log(this.offset)
    if (this.offset >= 10){
      const n = await this.marvelService.getCharacters(this.searchPhrase, 10, this.offset-10)
      this.previousPage= n
    }
    if (this.offset==0){
      this.previousButton = false
    }
    

  }

  async next(){
    this.previousButton = true
    this.offset+=10
    this.previousPage=this.characters
    this.characters=this.nextPage
    const n = await this.marvelService.getCharacters(this.searchPhrase, 10, this.offset + 10)
    if (n.length == 0){
      this.nextButton = false
    } else {
      this.nextPage=n
    }
    
  }
  
  

}
