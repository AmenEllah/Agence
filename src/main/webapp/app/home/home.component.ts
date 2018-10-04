import { Component, OnInit } from '@angular/core';
import {HttpResponse} from "@angular/common/http";
import {Voyage} from "app/shared/model/voyage.model";
import {VoyageService} from "app/entities/voyage";

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    voyages:Voyage[]=[];
    constructor(private voyageService:VoyageService){}
    loadAll(){
        this.voyageService.query().subscribe(
            (res:HttpResponse<Voyage[]>)=>{
                console.log(res);
                this.voyages=res.body;
            }
        );
    }
    ngOnInit(){
        console.log("ngOnInit...");
        this.loadAll();
    }
}
