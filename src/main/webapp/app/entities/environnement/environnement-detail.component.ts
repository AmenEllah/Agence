import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnvironnement } from 'app/shared/model/environnement.model';

@Component({
    selector: 'jhi-environnement-detail',
    templateUrl: './environnement-detail.component.html'
})
export class EnvironnementDetailComponent implements OnInit {
    environnement: IEnvironnement;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ environnement }) => {
            this.environnement = environnement;
        });
    }

    previousState() {
        window.history.back();
    }
}
