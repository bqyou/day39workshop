import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Character } from '../model/Character';
import { MarvelserviceService } from '../service/marvelservice.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Comment } from '../model/Comment';

@Component({
  selector: 'app-characterdetails',
  templateUrl: './characterdetails.component.html',
  styleUrls: ['./characterdetails.component.css']
})
export class CharacterdetailsComponent implements OnInit, OnDestroy {

  charId=""
  param$!: Subscription
  character!: Character
  comment=false
  form!: FormGroup
  comments:Comment[]=[]
  


  constructor(private activatedRoute: ActivatedRoute, private router: Router,
        private marvelService: MarvelserviceService, private fb: FormBuilder){
          
        }

  addComment(){
    this.comment=true
  }

  ngOnInit(): void {
      this.param$=this.activatedRoute.params.subscribe(
        async (params)=>{
          this.charId = params['charId']
          const details = await this.marvelService.getCharacterDetails(this.charId)
          this.character = details
          const ll = await this.marvelService.getComments(this.charId)
          this.comments = ll
          console.log(this.comments)       
        }
      )
      this.form = this.createForm()
               
  }

  ngOnDestroy(): void {
      this.param$.unsubscribe()
  }

  createForm(): FormGroup{
    return this.fb.group({
      comment: this.fb.control("")
    })
  }

  async postComment(){
    const commentForm = this.form?.value['comment']
    const c = {} as Comment
    c.comment = commentForm
    c.charId = this.charId
    this.marvelService.saveComment(c)
    alert("Comment posted")
    this.form?.controls['comment'].setValue('');
    const ll = await this.marvelService.getComments(this.charId)
    this.comments = ll
  }

}
