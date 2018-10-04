import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConfort } from 'app/shared/model/confort.model';

@Component({
    selector: 'jhi-confort-detail',
    templateUrl: './confort-detail.component.html'
})
export class ConfortDetailComponent implements OnInit {
    confort: IConfort;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ confort }) => {
            this.confort = confort;
        });
    }

    previousState() {
        window.history.back();
    }
}
