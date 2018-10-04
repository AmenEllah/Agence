import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IVoyage } from 'app/shared/model/voyage.model';

@Component({
    selector: 'jhi-voyage-detail',
    templateUrl: './voyage-detail.component.html'
})
export class VoyageDetailComponent implements OnInit {
    voyage: IVoyage;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ voyage }) => {
            this.voyage = voyage;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
