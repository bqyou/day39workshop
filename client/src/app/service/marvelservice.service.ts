import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Character } from '../model/Character';
import { Comment } from '../model/Comment';

@Injectable({
  providedIn: 'root'
})
export class MarvelserviceService {

  private APIURL = '/api/characters'

  constructor(private httpClient: HttpClient) { }

  getCharacters(searchPhrase: string, limit: number, offset: number){
    const params= new HttpParams()
                      .set('searchPhrase', searchPhrase)
                      .set('limit', limit)
                      .set('offset', offset)

    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8')

    return lastValueFrom(this.httpClient.get<Character[]>(this.APIURL,{params: params, headers: headers}))
  }

  getCharacterDetails(charId: string): Promise<any>{
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8')
    return lastValueFrom(this.httpClient.get<Character[]>(this.APIURL+'/'+charId,{headers: headers}))
  }

  saveComment(c:Comment) : Promise<any>{
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const body=JSON.stringify(c);
    console.log("save comment !");
    return lastValueFrom(this.httpClient.post<Comment>(this.APIURL+"/" + c.charId, body, {headers: headers}));
  }

  getComments(charId:string): Promise<Comment[]>{
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8')
    return lastValueFrom(this.httpClient.get<Comment[]>(this.APIURL+"/comments/"+charId, {headers: headers}));
  }
}



