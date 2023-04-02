import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ThemePalette } from '@angular/material/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-searchbar',
  templateUrl: './searchbar.component.html',
  styleUrls: ['./searchbar.component.css']
})
export class SearchbarComponent implements OnInit, OnDestroy {

  form!: FormGroup
  searchPhrase?: string

  constructor(private fb: FormBuilder, private router:Router){}

  ngOnInit(): void {
      this.form = this.createForm()
  }

  createForm():FormGroup{
    return this.fb.group({
      searchPhrase: this.fb.control('')
    })
  }

  search(){
    const searchPhrase = this.form?.value['searchPhrase']
    console.log(searchPhrase)
    this.router.navigate(['/list', searchPhrase])
  }

  ngOnDestroy(): void {
      
  }


  

}
