import { TestBed } from '@angular/core/testing';
import { BookComponent } from './book.component'; 

describe('BookComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookComponent],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(BookComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have the 'bookapp' title`, () => {
    const fixture = TestBed.createComponent(BookComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('bookapp');
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(BookComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Hello, bookapp');
  });
});
