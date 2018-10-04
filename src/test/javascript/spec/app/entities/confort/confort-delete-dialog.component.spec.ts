/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AgenceTestModule } from '../../../test.module';
import { ConfortDeleteDialogComponent } from 'app/entities/confort/confort-delete-dialog.component';
import { ConfortService } from 'app/entities/confort/confort.service';

describe('Component Tests', () => {
    describe('Confort Management Delete Component', () => {
        let comp: ConfortDeleteDialogComponent;
        let fixture: ComponentFixture<ConfortDeleteDialogComponent>;
        let service: ConfortService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [ConfortDeleteDialogComponent]
            })
                .overrideTemplate(ConfortDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConfortDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfortService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
