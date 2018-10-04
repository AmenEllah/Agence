import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHebergement } from 'app/shared/model/hebergement.model';

@Component({
    selector: 'jhi-hebergement-detail',
    templateUrl: './hebergement-detail.component.html'
})
export class HebergementDetailComponent implements OnInit {
    hebergement: IHebergement;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hebergement }) => {
            this.hebergement = hebergement;
        });
    }

    previousState() {
        window.history.back();
    }
}
