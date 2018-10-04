import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConfort } from 'app/shared/model/confort.model';
import { ConfortService } from './confort.service';

@Component({
    selector: 'jhi-confort-delete-dialog',
    templateUrl: './confort-delete-dialog.component.html'
})
export class ConfortDeleteDialogComponent {
    confort: IConfort;

    constructor(private confortService: ConfortService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.confortService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'confortListModification',
                content: 'Deleted an confort'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-confort-delete-popup',
    template: ''
})
export class ConfortDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ confort }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ConfortDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.confort = confort;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
